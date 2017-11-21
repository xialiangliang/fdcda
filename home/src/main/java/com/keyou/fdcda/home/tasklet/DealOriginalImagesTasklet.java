package com.keyou.fdcda.home.tasklet;

import java.io.File;

import org.apache.poi.ss.formula.functions.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.keyou.fdcda.api.utils.DateUtil;

public class DealOriginalImagesTasklet  implements Tasklet{

	private static final Logger logger = LoggerFactory.getLogger(DealOriginalImagesTasklet.class);

	private static final String rootPath = "/mnt/facepics";
	private static final String originalPath = "/original";
	private static final String dealPath = "/deal";
	
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		logger.info(" DealOriginalImagesTasklet 任务执行："+ DateUtil.getDate(DateUtil.DATETIME_FORMAT));
		//扫描根目录，扫描当天未处理的图片
		//图片服务器上根目录：/usr/local/facepics/original/设备id/日期
		//挂载服务器目录为:/mnt/facepics  
		//处理后的图片会从original拷贝到deal目录下面，同时original目录的图片删除掉节约空间
		//获取今天日期yyyy-mm-dd
		
		String todayFileName = DateUtil.getDate(DateUtil.DATE_FORMAT);
		File file = new File(rootPath+originalPath);
		if (file.exists()&&file.isDirectory()) {
			File [] files =  file.listFiles();
			//遍历设备
			for (int i = 0; i < files.length; i++) {
				File comoraFile = files[i];
				if (comoraFile.isDirectory()) {
					//日期文件夹
					File [] dateFiles=comoraFile.listFiles();
					for (int j = 0; j < dateFiles.length; j++) {
						if (dateFiles[j].isDirectory() && todayFileName.equals(dateFiles[j].getName())) {
							File[] images = dateFiles[j].listFiles();
							for (int k = 0; k < images.length; k++) {
								if (images[k].isFile() && images[k].isFile()) {
									
								}
							}
 						}
					}
				}
				
			}
		}
		
		
		return RepeatStatus.FINISHED;
	}

}
