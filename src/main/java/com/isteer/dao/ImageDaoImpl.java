package com.isteer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.isteer.module.ImageModule;

@Repository
public class ImageDaoImpl implements ImageDao {

	private final String INSERT_IMAGE = "INSERT INTO FILESYSTEM (FILEPATH,FILEUUID) VALUES(?,?)";
	private final String GET_IMAGE = "SELECT FILEPATH FROM FILESYSTEM WHERE FILEUUID=?";
	private final String GET_ALL_IMAGE="SELECT FILEUUID FROM FILESYSTEM";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void addFileToFileSystem(List<ImageModule> modules) {

		jdbcTemplate.batchUpdate(INSERT_IMAGE, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, modules.get(i).getFilePath());
				ps.setString(2, modules.get(i).getFileUUID());			
			}
			
			@Override
			public int getBatchSize() {
				
				return modules.size();
			}
		});

	}

	@Override
	public String getFileFromFileSystem(String fileUUID) {

		return jdbcTemplate.query(GET_IMAGE, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String filePath=null;
				while (rs.next()) {

					filePath=rs.getString("filePath");
					
				}
				return filePath;
			}

		},fileUUID);

	}

	@Override
	public List<String> getAllFilesFromFileSystem() {
		
		return jdbcTemplate.query(GET_ALL_IMAGE, rs -> 
		{
			List<String> fileUUID=new ArrayList<>();
			while(rs.next())
			{
				fileUUID.add(rs.getString("FILEUUID"));
				
			}
			return fileUUID;
			
		});
	}

}
