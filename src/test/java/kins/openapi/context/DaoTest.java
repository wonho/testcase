package kins.openapi.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.base.system.dao.CommonDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestDaoConfig.class,loader=AnnotationConfigContextLoader.class)
@Ignore
public class DaoTest {

	Logger logger = LoggerFactory.getLogger(DaoTest.class);

	@Autowired
	CommonDao dao;
	
	@Ignore
	@Test
	public void getDao() {
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		List<?> userList = dao.queryForList("user.select",  null);
		
		logger.debug("{}",userList);
		
	}
}
