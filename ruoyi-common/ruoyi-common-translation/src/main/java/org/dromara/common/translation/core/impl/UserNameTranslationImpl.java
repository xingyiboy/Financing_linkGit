package org.dromara.common.translation.core.impl;

import org.dromara.common.core.service.FlSpecificationService;
import org.dromara.common.core.service.UserService;
import org.dromara.common.translation.annotation.TranslationType;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.common.translation.core.TranslationInterface;
import lombok.AllArgsConstructor;

/**
 * 暂时改为翻译FlSpecificationVo
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NAME)
public class UserNameTranslationImpl implements TranslationInterface<String> {

    private final FlSpecificationService specificationService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return specificationService.queryByIdTranslation((Long) key);
        }

        return null;
    }
}
