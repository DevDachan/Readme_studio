// DAO : spring boot 코드를 작성하는 영역
// 데이터베이스에 접근하는 객체를 의미
// service가 db에 연결할 수 있게 해주는 역할
// db를 사용하여 데이터를 조회하거나 조작하는 기능을 전담
// repository를 활용해서 사용하기 때문에, 접근하는 본질은 repository가 가지고 있고,
// 메소드들을 활용해주는게 DAO -> 직접적으로 사용하는 것은 DAO

package com.readme.rss.data.dao;

import com.readme.rss.data.entity.TemplateEntity;

public interface TemplateDAO {
    TemplateEntity saveTemplate(TemplateEntity templateEntity);
    TemplateEntity getTemplate (String templateId);
}
