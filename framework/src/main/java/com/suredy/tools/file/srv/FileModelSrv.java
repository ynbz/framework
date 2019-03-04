package com.suredy.tools.file.srv;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.tools.file.model.FileModel;

/**
 * 文件操作服务
 * 
 * @author VIVID.G
 * @since 2016-11-1
 * @version v0.1
 */
@Service
public class FileModelSrv extends BaseSrvWithEntity<FileModel> {

	@Override
	public DetachedCriteria getDc(FileModel t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.like("name", t.getName(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(t.getSuffix())) {
			dc.add(Restrictions.eq("suffix", t.getSuffix()));
		}
		if (!StringUtils.isBlank(t.getRelativeFilePath())) {
			dc.add(Restrictions.eq("relativeFilePath", t.getRelativeFilePath()));
		}
		
		if (!StringUtils.isBlank(t.getShaCode())) {
			dc.add(Restrictions.eq("shaCode", t.getShaCode()));
		}
		
		if (!StringUtils.isBlank(t.getExtendData())) {
			dc.add(Restrictions.like("extendData", t.getExtendData(), MatchMode.ANYWHERE));
		}

		return dc;
	}

	@Transactional
	public FileModel save(String name, String suffix, String relativeFilePath, long size, String shaCode, String extendData) {
		FileModel model = new FileModel();

		model.setName(name);
		model.setRelativeFilePath(relativeFilePath);
		model.setSize(size);
		model.setSuffix(suffix);
		model.setShaCode(shaCode);
		model.setUploadTime(new Date());
		model.setExtendData(extendData);

		return this.save(model);
	}

}
