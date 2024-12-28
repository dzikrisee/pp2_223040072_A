package StudiKasus4.model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // Cetak debug informasi
            System.out.println("Mencoba memuat konfigurasi MyBatis...");

            // Pastikan file ada di resources folder
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();

            System.out.println("Konfigurasi MyBatis berhasil dimuat.");
        } catch (IOException e) {
            // Cetak detail error
            System.err.println("Kesalahan memuat konfigurasi MyBatis:");
            e.printStackTrace();
            throw new RuntimeException("Kesalahan memuat konfigurasi MyBatis: " + e.getMessage(), e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}