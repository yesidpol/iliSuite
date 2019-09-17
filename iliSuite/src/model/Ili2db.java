package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.IPluginDb;
import util.exception.ExitException;
import util.params.EnumParams;

public class Ili2db implements InterlisExecutable {
	private IPluginDb plugin; // 2pg, 2gpkg, 2mssql   <-- param?
	
	// TODO Change key for class or enum?
	private HashMap<String, String> paramsMap;
	
	boolean stop;
	
	public Ili2db() {
		paramsMap = new HashMap<String, String>();
		plugin = null;
	}
	
	@Override
	public void addParam(String param, String value) {
		paramsMap.put(param, value);
	}
	
	@Override
	public void removeParam(String param) {
		paramsMap.remove(param);
	}
	
	@Override
	public void run() {
		String[] args = getCommand().toArray(new String[0]);
		stop = false;
		// executor.execute(runnableTask);
		try {
			plugin.runMain(args);
			stop = true;
			// return true;
		} catch (ExitException e) {
			System.out.println(e.status);
			stop = true; 
			// return false;
		}
	}
	
	@Override
	public List<String> getCommand(){
		HashMap<String, String> params = (HashMap<String, String>) paramsMap.clone();
		List<String> result = new ArrayList<String>();
		String finalPath = paramsMap.get(EnumParams.FILE_NAME.getName());
		
		if(finalPath != null && !finalPath.isEmpty())
			paramsMap.remove(EnumParams.FILE_NAME.getName());
		
		for(Map.Entry<String, String> item:paramsMap.entrySet()){
			String key = item.getKey();
			String value = item.getValue();
			result.add(key);
			
			if(value != null && !value.isEmpty() && !value.equals("true")){
				result.add(value);
			}
		}
		
		if(finalPath != null && !finalPath.isEmpty())
			result.add(finalPath);
		
		return result;
	}
	
	public IPluginDb getPlugin() {
		return plugin;
	}
	public void setPlugin(IPluginDb plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public String getArgs(boolean hideSensitiveData) {
		List<String> command = getCommand();
		
		if (hideSensitiveData) {
			int index = command.indexOf("--dbpwd");
			if(index != -1) {
				command.set(index+1, "**********");
			}
		}
		
		String result = String.join(" ", command);
		
		return result;
	}
}
