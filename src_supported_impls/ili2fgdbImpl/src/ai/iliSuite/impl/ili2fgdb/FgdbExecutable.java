package ai.iliSuite.impl.ili2fgdb;

import java.util.List;

import ai.iliSuite.base.IliExecutable;
import ch.ehi.ili2fgdb.FgdbMain;

public class FgdbExecutable implements IliExecutable {

	@Override
	public void run(List<String> params) {
		String[] args = params.toArray(new String[0]);
		(new FgdbMain()).domain(args);
	}

}
