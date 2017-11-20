package com.keyou.fdcda.home.tasklet;

import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.keyou.fdcda.api.utils.DateUtil;

public class DealOriginalImagesTasklet  implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		System.out.println("任务执行："+ DateUtil.getDate(DateUtil.DATETIME_FORMAT));
		
		return RepeatStatus.FINISHED;
	}

}
