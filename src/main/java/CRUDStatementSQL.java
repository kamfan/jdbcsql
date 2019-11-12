import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDStatementSQL {

    private Connection conn;

    public CRUDStatementSQL(Connection conn) {
        this.conn = conn;
    }

    public void selectSQLbyJDBC(List<String> columnNames, String tableName) {
        try {
            Statement statement = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ");
            for(String columnName : columnNames) {
                if (columnNames.get(columnNames.size()-1).equals(columnName))
                    sb.append(columnName);
                else {
                    sb.append(columnName).append(", ");
                }

            }
            sb.append(" from ").append(tableName);
            ResultSet rs = statement.executeQuery(sb.toString());
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);

                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Statement updateSQLbyJDBC(Map<String,String> updateQuery,String tableName, String whereCondition) {
        Statement statement= null;
        try {
            statement = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(tableName);
            sb.append(" SET ");

            int i=0;
            for(Map.Entry<String, String> entry : updateQuery.entrySet()) {
                if( i < updateQuery.entrySet().size()-1) {
                    sb.append(entry.getKey() + " = " + "'" + entry.getValue() +"'" + ", ");
                    i++;
                }
                else {
                    sb.append(entry.getKey() + " = " + "'" + entry.getValue() +"'" + " ");
                }
            }
            sb.append(whereCondition);
            System.out.println(sb);
            statement.executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public Statement deleteSQLbyJDBC(String tableName, String whereCondition) {
        Statement statement= null;
        try {
            statement = conn.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE ");
            sb.append(tableName);
            sb.append(" WHERE ");
            sb.append(whereCondition);
            System.out.println(sb);
            statement.executeQuery(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
//    public Statement addEmployee() {
//        JSONArray jsonArrayEmployees = new JSONArray("http://dummy.restapiexample.com/api/v1/employees");
//        List<Employee> employeesList = new ArrayList<>();
//
//        for (int i = 0; i < jsonArrayEmployees.length(); i++) {
//            JSONObject one = (JSONObject) jsonArrayEmployees.get(i);
//            Employee employee = new Employee();
//            employee.setId(Integer.parseInt(one.get("id").toString()));
//            employee.setAge(Double.parseDouble(one.get("employee_age").toString()));
//            employee.setName(one.get("employee_name").toString());
//            employee.setSalary(Double.parseDouble(one.get("employee_salary").toString()));
//            employeesList.add(employee);
//        }
//
//        Statement statement = null;
//        try {
//            statement = conn.createStatement();
//            StringBuilder sb = new StringBuilder();
//            for (Employee e : employeesList) {
//                sb.append("INSERT INTO Employees (LastName, FirstName, Address, City, Salary, Age, StartJobDate, Benefit) VALUES (");
//                sb.append(e.getName());
//                sb.append(", 'default', 'default', 'default', ");
//                sb.append(e.getSalary());
//                sb.append(", ");
//                sb.append(e.getAge());
//                sb.append(", default, default);");
//                System.out.println(sb);
//                statement.executeQuery(sb.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return statement;
//    }
}
