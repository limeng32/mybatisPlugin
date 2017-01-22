package limeng32.mybatis.mybatisPlugin.mapperPlugin.builder;

public class TableName {

	public TableName(String tableName, int index) {
		this.tableName = tableName;
		this.index = index;
	}

//	public TableName(String tableName) {
//		this.tableName = tableName;
//	}

	private String tableName;

	private int index = 0;

	public String sqlSelect() {
		return tableName + " as " + tableName + "_" + index + " ";
	}

	public String sqlWhere() {
		return tableName + "_" + index + ".";
	}

	public int getIndex() {
		return index;
	}

}
