package top.coderak.core.base.handler;

import top.coderak.core.utils.DataConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MybatisJsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    public MybatisJsonTypeHandler(Class<T> clazz) {

        if (clazz == null) {

            throw new IllegalArgumentException("Type argument cannot be null");
        }

        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {

        ps.setString(i, DataConverter.convertObject2Json(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return (T) DataConverter.convertJson2Object(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return (T) DataConverter.convertJson2Object(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        return (T) DataConverter.convertJson2Object(cs.getString(columnIndex), clazz);
    }
}
