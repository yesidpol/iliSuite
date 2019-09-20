package model;

import base.IPluginDb;
import iliSuite.plugin.ili2pg.Ili2pgPlugin;
import util.params.EnumParams;

public class TestModel {
	public static void main(String[] args){
		/*IPluginDb ili2pg = new Ili2pgPlugin();
		Ili2db model = new Ili2db();
		model.setPlugin(ili2pg);
		model.addParam(EnumParams.FILE_NAME.getName(), "c:\\abc.ili");
		//model.addParam(EnumParams.HELP.getName(), null);
		//model.addParam("--dbpwd", "12345678");
		model.addParam(EnumParams.SCHEMA_IMPORT.getName(), null);
		System.out.println(model.getArgs(true));*/
		
		InterlisExecutable umlEditor = new UmlEditor();
		
		umlEditor.run();
		
		InterlisExecutable validator = new IliValidator();
		
		validator.addParam("--help", null);
		validator.run();
	}
}
