package ai.iliSuite.impl.ili2pg;

import java.util.List;

import ai.iliSuite.base.IliExecutable;
import ch.ehi.ili2pg.PgMain;

public class PgExecutable implements IliExecutable {

	@Override
	public void run(List<String> params) {
		String[] args = params.toArray(new String[0]);
		(new PgMain()).domain(args);
	}

}
