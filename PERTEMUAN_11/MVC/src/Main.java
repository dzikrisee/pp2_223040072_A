package PERTEMUAN_11.MVC.src;

import PERTEMUAN_11.MVC.src.controller.UserController;
import PERTEMUAN_11.MVC.src.model.MyBatisUtil;
import PERTEMUAN_11.MVC.src.model.UserMapper;
import PERTEMUAN_11.MVC.src.view.UserView;
import org.apache.ibatis.session.SqlSession;

public class Main {
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        UserView view = new UserView();
        new UserController(view, mapper);

        view.setVisible(true);
    }
}
