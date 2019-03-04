package com.suredy.app.files.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.files.entity.FolderEntity;
import com.suredy.app.files.entity.FolderPermissionEntity;
import com.suredy.app.files.entity.UploadFileEntity;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.mvc.model.TreeMenu;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service("UploadFileSrv")
public class UploadFileSrv extends BaseSrvWithEntity<UploadFileEntity> {

	@Override
	public DetachedCriteria getDc(UploadFileEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}

		if (t.getFolders() != null && !t.getFolders().isEmpty()) {
			dc.add(Restrictions.in("folder", t.getFolders()));
		}
		return dc;
	}

	public List<UploadFileEntity> getByType(List<FolderEntity> folders) {
		UploadFileEntity search = new UploadFileEntity();

		if (folders != null && !folders.isEmpty()) {
			search.setFolders(folders);
		}
		List<UploadFileEntity> pos = this.readByEntity(search);

		return pos == null ? null : pos;
	}

	public Integer Count(List<FolderEntity> folders) {

		UploadFileEntity search = new UploadFileEntity();
		if (folders != null && !folders.isEmpty()) {
			search.setFolders(folders);
		}
		int ret = this.getCountByEntity(search);
		return ret;
	}

	public List<UploadFileEntity> getAll(int page, int size, List<FolderEntity> folders) {
		OrderEntity oe = new OrderEntity();
		oe.add("uploadTime", false);

		UploadFileEntity search = new UploadFileEntity();
		if (folders != null && !folders.isEmpty()) {
			search.setFolders(folders);
		}
		List<UploadFileEntity> pos = this.readPageByEntity(search, page, size, oe);

		return pos == null ? null : pos;
	}

	@Transactional
	public List<Tree> getFolderTree(@Nullable String rootId) {

		String ql = "SELECT T FROM FolderEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " ORDER BY T.sort";
		@SuppressWarnings("unchecked")
		List<FolderEntity> entities = (List<FolderEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (FolderEntity entity : entities) {
			Tree entityTree = buildTree(entity);
			if (entityTree != null) {
				ret.add(entityTree);
			}
		}

		return ret;
	}

	private Tree buildTree(FolderEntity entity) {
		if (entity == null) {
			return null;
		}
		Tree node = new Tree();
		node.setText(entity.getName());
		node.setData(entity.toVO());

		if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				node.setChildren(new ArrayList<Tree>());
			}
			for (FolderEntity typeEntity : entity.getChildren()) {
				Tree child = buildTree(typeEntity);
				if (child != null) {
					node.getChildren().add(child);
				}
			}
		}
		return node;
	}
	
	@Transactional
	public List<String> getAllowedFolders(@Nullable String rootId, @Nullable List<String> roles, @Nullable List<String> units) {
		String ql = "SELECT T FROM FolderEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " ORDER BY T.sort";
		
		@SuppressWarnings("unchecked")
		List<FolderEntity> entities = (List<FolderEntity>) this.readByQL(ql);
		List<String> data = new ArrayList<String>();
		for (FolderEntity entity : entities) {
			setFolders(data, entity, roles, units);
		}

		return data;
	}
	
	public void changeFileStatus(String ids,int status){
		String[] idList = ids.split(",");
		for(String id : idList){
			String ql = "UPDATE UploadFileEntity T SET T.status='"+status+"' where id='"+id+"'";
			this.updateByQL(ql);
		}
	}
	
	private void setFolders(List<String> data, FolderEntity entity, @Nullable List<String> roles, @Nullable List<String> units) {
		if (entity == null) {
			return;
		}
		if (entity.getIsPublic() == 1) { // 开放访问的文件分类
			data.add(entity.getId());
			if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
				for (FolderEntity typeEntity : entity.getChildren()) {
					setFolders(data, typeEntity, roles, units);
				}
			}
		} else { // 授权访问的文件分类
			boolean avaiable = false;
			List<FolderPermissionEntity> permissions = entity.getAssociationPermissions();
			List<String> allowedRoles = new ArrayList<String>();
			List<String> allowedUnits = new ArrayList<String>();
			if (permissions != null && !permissions.isEmpty()) {
				for (FolderPermissionEntity temp : permissions) {
					if (temp.getSubjectType() == 2) { // roles
						allowedRoles.add(temp.getSubject());
					} else if (temp.getSubjectType() == 3) { // units
						allowedUnits.add(temp.getSubject());
					}
				}
				permissions.clear();
			}
			// 已授权访问的部门是否是用户所在的部门
			if (!avaiable) {
				if (units != null && !units.isEmpty()) {
					for (String id : units) {
						if (allowedUnits.contains(id)) {
							avaiable = true;
							break;
						}
					}
				}
			}
			// 已授权访问的角色是否包含用户所映射的角色
			if (!avaiable) {
				if (roles != null && !roles.isEmpty()) {
					for (String id : roles) {
						if (allowedRoles.contains(id)) {
							avaiable = true;
							break;
						}
					}
				}
			}

			// 用户可访问此节点
			if (avaiable) {
				data.add(entity.getId());
				if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
					for (FolderEntity typeEntity : entity.getChildren()) {
						setFolders(data, typeEntity, roles, units);
					}
				}
			}
		}
	}

	@Transactional
	public List<TreeMenu> getFolderTreeMenu(@Nullable String rootId, @Nullable List<String> roles, @Nullable List<String> units) {
		String ql = "SELECT T FROM FolderEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " ORDER BY T.sort";
		
		@SuppressWarnings("unchecked")
		List<FolderEntity> entities = (List<FolderEntity>) this.readByQL(ql);
		List<TreeMenu> ret = new ArrayList<TreeMenu>();
		for (FolderEntity entity : entities) {
			TreeMenu entityTree = buildTreeMenu(entity, roles, units);
			if (entityTree != null) {
				ret.add(entityTree);
			}
		}

		return ret;
	}

	private TreeMenu buildTreeMenu(FolderEntity entity, @Nullable List<String> roles, @Nullable List<String> units) {
		if (entity == null) {
			return null;
		}
		if (entity.getIsPublic() == 1) { // 开放访问的文件分类
			TreeMenu node = new TreeMenu();
			node.setText(entity.getName());
			// 指定跳转到报表资源展示页面
			node.setUri("files/document/list?typeId=" + entity.getId());

			if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					node.setChildren(new ArrayList<TreeMenu>());
				}
				for (FolderEntity typeEntity : entity.getChildren()) {
					TreeMenu child = buildTreeMenu(typeEntity, roles, units);
					if (child != null) {
						node.getChildren().add(child);
					}
				}
			}
			return node;
		} else { // 授权访问的文件分类
			boolean avaiable = false;
			List<FolderPermissionEntity> permissions = entity.getAssociationPermissions();
			List<String> allowedRoles = new ArrayList<String>();
			List<String> allowedUnits = new ArrayList<String>();
			if (permissions != null && !permissions.isEmpty()) {
				for (FolderPermissionEntity temp : permissions) {
					if (temp.getSubjectType() == 2) { // roles
						allowedRoles.add(temp.getSubject());
					} else if (temp.getSubjectType() == 3) { // units
						allowedUnits.add(temp.getSubject());
					}
				}
				permissions.clear();
			}
			// 已授权访问的部门是否是用户所在的部门
			if (!avaiable) {
				if (units != null && !units.isEmpty()) {
					for (String id : units) {
						if (allowedUnits.contains(id)) {
							avaiable = true;
							break;
						}
					}
				}
			}
			// 已授权访问的角色是否包含用户所映射的角色
			if (!avaiable) {
				if (roles != null && !roles.isEmpty()) {
					for (String id : roles) {
						if (allowedRoles.contains(id)) {
							avaiable = true;
							break;
						}
					}
				}
			}
			// 用户可访问此节点
			if (avaiable) {
				TreeMenu node = new TreeMenu();
				node.setText(entity.getName());
				// 指定跳转到报表资源展示页面
				node.setUri("files/document/list?typeId=" + entity.getId());

				if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
					if (node.getChildren() == null || node.getChildren().isEmpty()) {
						node.setChildren(new ArrayList<TreeMenu>());
					}
					for (FolderEntity typeEntity : entity.getChildren()) {
						TreeMenu child = buildTreeMenu(typeEntity, roles, units);
						if (child != null) {
							node.getChildren().add(child);
						}
					}
				}
				return node;
			}
			// 用户不可访问此节点
			return null;
		}
	}
}
