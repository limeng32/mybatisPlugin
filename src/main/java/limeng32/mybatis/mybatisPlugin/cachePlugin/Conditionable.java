package limeng32.mybatis.mybatisPlugin.cachePlugin;

public interface Conditionable {

	public Limitable getLimiter();

	public void setLimiter(Limitable limiter);

	public Sortable getSorter();

	public void setSorter(Sortable sorter);

	public String dot = ".";

	public enum Sequence {
		asc, desc
	}

}
