package com.jcute.core.resource.abs;

import java.io.IOException;
import java.io.InputStream;

import com.jcute.core.resource.Resource;

public abstract class AbstractResource implements Resource{

	@Override
	public boolean isExists(){
		try{
			InputStream inputStream = this.getFileInputStream();
			inputStream.close();
			return true;
		}catch(Throwable t){
			return false;
		}
	}

	@Override
	public long getFileLength() throws IOException{
		InputStream inputStream = this.getFileInputStream();
		try{
			long size = 0;
			byte[] buffer = new byte[255];
			int read;
			while((read = inputStream.read(buffer)) != -1){
				size += read;
			}
			return size;
		}finally{
			try{
				inputStream.close();
			}catch(IOException e){}
		}
	}

}