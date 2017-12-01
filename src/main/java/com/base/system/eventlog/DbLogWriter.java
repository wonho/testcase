package com.base.system.eventlog;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbLogWriter implements BatchWriter<LogSession> {

	Logger logger = LoggerFactory.getLogger(DbLogWriter.class);

	private static String LOG_SQL ="INSERT INTO TB_RSM380L A"+
	"(A.CONN_RQST_SEQ, A.CONN_RQST_DT, A.CNTN_TYP_NM, A.INNR_ADDR, A.RQST_URL, A.RLAY_USER_NM,"+
	"A.CNTN_PATH, A.EXTRL_ADDR, A.SERV_NM, A.MSG_DIV_NM, A.MSG_CNTN,"+
	"A.FRST_REGTR_ID, A.FRST_REG_DTTM, A.LAST_UPDTR_ID, A.LAST_UPDT_DTTM)"+
	" values (?,TO_CHAR(SYSDATE, 'YYYYMMDD'),?,?,?,?,?,?,?,?,?,'kofons_api',TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),'kofons_api',TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'))";
	
	private JdbcTemplate template;

    @Resource(name="dataSource")
 	public void setDataSource(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	public void write(final List<LogSession> logEvents) {
		for (LogSession logSession : logEvents) {
			logger.debug("DbLogWriter -> event Log : {}",logSession.toString());
		}
		
		template.batchUpdate(LOG_SQL,new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				String timeseq = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Timestamp(System.currentTimeMillis()));
				
				LogSession logSession = logEvents.get(i);
				
				ps.setString(1, timeseq);
				ps.setString(2, logSession.getContentType());
				ps.setString(3, logSession.getLocalAddr());
				ps.setString(4, logSession.getRequestURI());
				ps.setString(5, logSession.getUserAgent());
				ps.setString(6, logSession.getContextPath());
				ps.setString(7, logSession.getRemoteAddr());
				ps.setString(8, logSession.getServiceName());
				ps.setString(9,logSession.getResult());
				ps.setString(10,logSession.getParameter());
			}
			
			public int getBatchSize() {
				return logEvents.size();
			}
		});		
	}
}
