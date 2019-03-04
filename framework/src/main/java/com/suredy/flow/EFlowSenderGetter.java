package com.suredy.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.mvc.model.Tree;
import com.suredy.flow.model.SenderData;
import com.suredy.security.service.UserSrv;

public class EFlowSenderGetter {

	@SuppressWarnings("rawtypes")
	public SenderData load(String xml, String[] para, String paraType) {
		
		UserSrv bs = (UserSrv) ApplicationContextHelper.getBeanByType(UserSrv.class);
		Document doc = null;
		SenderData senderData = new SenderData();
		try {
			doc = DocumentHelper.parseText(xml);
			
			List sendNodes = doc.selectNodes("/Return/SendArray/Send");

			for (int n = 0; n < sendNodes.size(); n++) {
				Element sendElement = (Element) sendNodes.get(n);
				List elist = sendElement.selectNodes("Receiver");
				List ulist = sendElement.selectNodes("Receiver/OU");
				List glist = sendElement.selectNodes("Receiver/Group");
				String gp = "";
				String up = "";
				String eCurUser = "";
				String eCurGroup = "";
				String excludeCurEditor = "";
				String cu = "";

				if ((elist != null) && (elist.size() > 0)) {
					Element el = (Element) elist.get(0);
					eCurUser = el.attribute("excludeCurUser") == null ? "" : el.attribute("excludeCurUser").getValue();
					eCurGroup = el.attribute("excludeGroup") == null ? "" : el.attribute("excludeGroup").getValue();
					excludeCurEditor = el.attribute("excludeCurEditor") == null ? "" : el.attribute("excludeCurEditor").getValue();
					if ((glist != null) && (glist.size() > 0)) {
						Element e = (Element) glist.get(0);
						gp = e.getText();
					}
					if ((ulist != null) && (ulist.size() > 0)) {
						Element e = (Element) ulist.get(0);
						up = e.getText();
					}
				}
				if (("Y".equals(eCurUser)) && (excludeCurEditor.equals("User"))) {
					List curUser = doc.selectNodes("/Return/User");
					cu = ((Element) curUser.get(0)).attribute("un").getValue();
					
				}
				String sqlStr = CreateQL(gp, up, eCurGroup, cu);
				List list = bs.readBySQL(sqlStr);
				if (list != null) {
					String tl = sendElement.attribute("topLayer").getValue();
					String obj = sendElement.attribute("obj").getValue();
					if ((tl.indexOf("OU") > -1) || (obj.indexOf("OU") > -1)) {
						senderData.setUsers(createUnitTree(list));
						
						if (senderData.getUsers().size() == 0)
							senderData.setText("无接收用户！");
					} else {
						for (int i = 0; i < list.size(); i++) {
							Tree senderUser = new Tree();
							HashMap<String,String> data = new HashMap<String,String>();
							data.put("nodeType", "user");
							data.put("id", ((Object[]) list.get(i))[2].toString());
							data.put("unitName", ((Object[]) list.get(i))[4].toString());
							senderUser.setText(((Object[]) list.get(i))[3].toString());
							senderUser.setData(data);
							senderData.getUsers().add(senderUser);
						}
					}

					senderData.setId(sendElement.attribute("id").getValue());
					senderData.setName(sendElement.attribute("name").getValue());
					senderData.setStyle(sendElement.attribute("style").getValue());
					senderData.setObj(sendElement.attribute("obj").getValue());
					senderData.setTopLayer(sendElement.attribute("topLayer").getValue());
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return senderData;
	}

	public static String CreateQL(String gp, String up, String eGroup, String cu) {
		String gpstr = "";
		String upstr = "";
		String eGroupstr = "";
		if (gp != "") {
			String[] g = gp.split(";");
			for (int i = 0; i < g.length; i++) {
				if (i == 0)
					gpstr = gpstr + "'" + g[i] + "'";
				else {
					gpstr = gpstr + ",'" + g[i] + "'";
				}
			}
		}
		if (up != "") {
			String[] g = up.split(";");
			for (int i = 0; i < g.length; i++) {
				if (i == 0)
					upstr = upstr + "wymc_zzdy like '%" + g[i] + "'";
				else {
					upstr = upstr + " or wymc_zzdy like'%" + g[i] + "'";
				}
			}
		}
		if (eGroup != "") {
			String[] g = eGroup.split(";");
			for (int i = 0; i < g.length; i++) {
				if (i == 0)
					eGroupstr = eGroupstr + "'" + g[i] + "'";
				else {
					eGroupstr = eGroupstr + ",'" + g[i] + "'";
				}
			}
		}
		String sql = "";
		if (gpstr == "") {
			sql = sql + "select id_e_yh,wymc_zzdy,wymc,mc,qc from t_e_yh where 1=1 ";
			if (upstr != "")
				sql = sql + " and (" + upstr + ")";
			if (eGroupstr != "")
				sql = sql + "  and wymc not in(select wymc from v_e_qz where groupName in(" + eGroupstr + "))";
			if (cu != "")
				sql = sql + " and  wymc!='" + cu + "'";
			sql = sql + " order by wymc_zzdy,plxh";
		} else {
			sql = sql + "select id_e_yh,wymc_zzdy,wymc,mc,qc from v_e_qz where groupName in (" + gpstr + ") ";
			if (upstr != "")
				sql = sql + " and (" + upstr + ") ";
			if (eGroupstr != "")
				sql = sql + " and groupName not in(" + eGroupstr + ") ";
			if (cu != "")
				sql = sql + " and  wymc!='" + cu + "'";
			sql = sql + " order by wymc_zzdy,plxh";
		}
		return sql;
	}

	public boolean isSelected(String[] para, String id) {
		for (String s : para) {
			if (s.equals(id))
				return true;
		}
		return false;
	}
	
	public static List<Tree> createUnitTree(List userList){
		List<Tree> nodes = new ArrayList<Tree>();
		for(int i=0;i<userList.size();i++){
			String unitIds[] = ((Object[]) userList.get(i))[1].toString().split("/");
			Tree unitNode=null;
			Tree parentNode=null;
			for(int j=unitIds.length-2;j>=0;j--){
				if(parentNode==null)
					unitNode = hasNode(nodes,unitIds[j]);
				else
					unitNode = hasNode(nodes,unitIds[j]+"/"+((HashMap<String,String>)parentNode.getData()).get("id"));
				if(unitNode==null)
				{
					unitNode = new Tree();
					String unitName = ((Object[]) userList.get(i))[4].toString();
					String unitNames[] = unitName.split("/");
					unitNode.setText(unitNames[j+1]);
					HashMap<String,String> data = new HashMap<String,String>();
					unitNode.setChildren(new ArrayList<Tree>());
					if(parentNode==null)
						data.put("id",unitIds[j]);
					else
						data.put("id",unitIds[j]+"/"+((HashMap<String,String>)parentNode.getData()).get("id"));
					data.put("type", "unit");
					unitNode.setData(data);
					if(parentNode==null)
						nodes.add(unitNode);
					else
						parentNode.getChildren().add(unitNode);
				}
				parentNode = unitNode;
			}
			List<Tree> children =unitNode.getChildren();
			if(children==null){
				children = new ArrayList<Tree>();
				unitNode.setChildren(children);
			}
			Tree treeUser = new Tree();
			HashMap<String,String> data = new HashMap<String,String>();
			treeUser.setText(((Object[]) userList.get(i))[3].toString());
			data.put("nodeType", "user");
			data.put("id",((Object[]) userList.get(i))[2].toString());
			data.put("unitName",((Object[]) userList.get(i))[4].toString());
			treeUser.setData(data);
			children.add(treeUser);
		}
		return nodes;
	}
	
	public static Tree hasNode(List<Tree> nodes,String code){
		if(nodes!=null){
			for(int i=0;i<nodes.size();i++){
				Tree tree = nodes.get(i);
				HashMap<String,String> data = (HashMap<String,String>)tree.getData();
				if("unit".equals(data.get("type"))&&code.equals(data.get("id"))){
					return tree;
				}else{
					Tree n =  hasNode(nodes.get(i).getChildren(),code);
						if(n!=null)
							return n;
				}
			}
		}
		return null;
	}
}
