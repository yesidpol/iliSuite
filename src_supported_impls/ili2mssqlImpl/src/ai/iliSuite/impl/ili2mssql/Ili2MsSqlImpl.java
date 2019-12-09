package ai.iliSuite.impl.ili2mssql;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.iliSuite.impl.DbDescription;
import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2mssql.dbconn.Ili2MsSqlScope;
import ai.iliSuite.impl.ili2mssql.dbconn.MsSqlConnection;
import ai.iliSuite.impl.ili2mssql.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2mssql.MsSqlMain;


public class Ili2MsSqlImpl implements ImplFactory {
	
	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2mssql.resources.application");
		AbstractMain mainApp = new MsSqlMain();
		String dbName = "MsSQL Server";
		String helpText = bundle.getString("database.description");
		String appName = mainApp.getAPP_NAME();
		String appVersion = mainApp.getVersion();
		
		return new DbDescription(appName, appVersion, dbName, helpText);
	}
	
	@Override
	public IController getController(AbstractConnection connection, boolean createSchema) throws IOException {
		return new DatabaseOptionsController(connection, createSchema);
	}

	@Override
	public int runMain(String[] args) {
		(new MsSqlMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(AbstractConnection connection){
		return new Ili2MsSqlScope(connection);
	}

	@Override
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType) {
		return null;
	}

	@Override
	public AbstractConnection getConnector() {
		return new MsSqlConnection();
	}
}
