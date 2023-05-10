// handler는 매겨변수로 dto의 변수를 넘겨주고 entity로 받는..?
// dao는 매겨변수로 entity를 넘겨주는,,?
// handler : data를 다루고 service로 넘겨주는 ..?

package com.readme.rss.data.handler;

import com.readme.rss.data.dto.FrameworkDTO;
import java.util.List;

public interface FrameworkHandler {
    void saveFramework(String type, String name, String content);

    FrameworkDTO getFramework(String name);

    List<String> getFrameworkNameList();

    String findContent(String name);

}
