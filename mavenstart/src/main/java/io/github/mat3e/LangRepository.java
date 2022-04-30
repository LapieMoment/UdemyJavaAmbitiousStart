package io.github.mat3e;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class LangRepository {

    Optional<Lang> findById(Integer id){
        var session = HibernateUtil.getSessionFactory().openSession();
        var tranaction = session.beginTransaction();
        var result = session.get(Lang.class, id);
        tranaction.commit();
        session.close();
        return Optional.ofNullable(result);
    }

}
