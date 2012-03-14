package model.test;

public abstract class AbstractTest implements Test {

	@Override
	public abstract void run();
	
	@Override
	public String getName() {
		return "Unknown Test";
	}

}
