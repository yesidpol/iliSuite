package ai.iliSuite.impl.ili2mssql;

import java.util.List;

import ai.iliSuite.base.IliExecutable;
import ch.ehi.ili2mssql.MsSqlMain;

public class MssqlExecutable implements IliExecutable {

	@Override
	public void run(List<String> params) {
		String[] args = params.toArray(new String[0]);
		(new MsSqlMain()).domain(args);
	}
}
