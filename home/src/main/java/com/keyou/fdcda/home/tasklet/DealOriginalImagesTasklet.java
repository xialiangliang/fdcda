package com.keyou.fdcda.home.tasklet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.keyou.fdcda.api.constants.ImageInfoConstants;
import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.api.utils.DateUtil;
import com.keyou.fdcda.api.utils.FileUtil;
import com.keyou.fdcda.app.dao.ImageInfoMapper;

public class DealOriginalImagesTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(DealOriginalImagesTasklet.class);

	private static final String rootPath = "/mnt/facepics/";
	private static final String originalPath = "original";
	private static final String dealPath = "deal";

	private static final List<String> imageTypeList = new ArrayList<>();

	static {
		imageTypeList.add("JPG");
		imageTypeList.add("JPEG");
		imageTypeList.add("BMP");
		imageTypeList.add("GIF");
		imageTypeList.add("PNG");
	}

	@Autowired
	private ImageInfoMapper imageInfoMapper;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		logger.info(" DealOriginalImagesTasklet 任务执行：" + DateUtil.getDate(DateUtil.DATETIME_FORMAT));
		try {
			// 先处理图片
			dealImage();
			// 同步到依图 调用存储过程prc_send_camera_image
			callYituPro();
		} catch (Exception e) {
			logger.error(" 执行设备图片入库出现异常： ",e);
		}

		return RepeatStatus.FINISHED;
	}
	
	private void callYituPro() throws Exception{
		imageInfoMapper.selectCallPro();
	}

	private void dealImage() throws Exception {
		// 扫描根目录，扫描当天未处理的图片
		// 图片服务器上根目录：/usr/local/facepics/original/设备id/日期
		// 挂载服务器目录为:/mnt/facepics
		// 处理后的图片会从original拷贝到deal目录下面，同时original目录的图片删除掉节约空间
		// 获取今天日期yyyy-mm-dd

		List<ImageInfo> imageInfos = new ArrayList<>();
		String todayFileName = DateUtil.getDate(DateUtil.DATE_FORMAT);
		File file = new File(rootPath + originalPath);
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			// 遍历设备
			for (int i = 0; i < files.length; i++) {
				File comoraFile = files[i];
				if (comoraFile.isDirectory()) {
					String camaeraCode = comoraFile.getName();
					// 日期文件夹
					File[] dateFiles = comoraFile.listFiles();
					for (int j = 0; j < dateFiles.length; j++) {
						if (dateFiles[j].isDirectory() && todayFileName.equals(dateFiles[j].getName())) {
							File[] images = dateFiles[j].listFiles();
							int length = images.length ;
							logger.info(todayFileName + " 此次任务需要处理： "+length + " 张图片.");
							for (int k = 0; k < length; k++) {
								if (images[k].isFile()) {
									String fileType = getFileType(images[k].getName());
									if (imageTypeList.contains(fileType.toUpperCase())) {
										String createFileDate = FileUtil.getFileModifiedTime(images[k]) ;
										String newPath = images[k].getPath().replace(originalPath, dealPath);
										// 拷贝 到处理后的目录，并删除原来的文件
										boolean result = dealFile(images[k], new File(newPath));

										if (result) {
											ImageInfo info = new ImageInfo();
											info.setCameraCode(camaeraCode);
											info.setCreateDate(DateUtil.getDate(createFileDate,DateUtil.DATETIME_FORMAT));
											info.setImageUrl(newPath);
											info.setDealFlag(ImageInfoConstants.IMAGE_STATUS_not_deal);
											imageInfos.add(info);
										}

									}
								}
							}
						}
					}
				}

			}
			if (imageInfos!=null && !imageInfos.isEmpty()) {
				imageInfoMapper.insertList(imageInfos);
			}
		}
	}

	private String getFileType(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	private boolean dealFile(File srcFile, File destFile) {

		boolean result = true;
		try {
			FileUtils.copyFile(srcFile, destFile);
			result = srcFile.delete();
		} catch (Exception e) {
			logger.error(" dealFile: " ,e);
			result = false;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		File srcFile = new File("D:\\mnt\\facepics\\original\\camera00000000101\\2017-11-9\\2017119938.jpg");
		System.out.println(srcFile.lastModified());
		
		FileUtils.copyFile(srcFile, new File("D:\\mnt\\facepics\\deal\\camera00000000101\\2017-11-9\\2017119938.jpg"));
	}
}
