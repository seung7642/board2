package com.neowiz.practice.commons.utils;

import com.neowiz.practice.commons.exception.NotValidException;
import lombok.extern.slf4j.Slf4j;

/**
 * User: SeungHo Lee (seung7642@neowiz.com)
 * Date: 2020. 2. 6.
 * Time: 오전 9:50
 */
@Slf4j
public class PreconditionUtils {

    public static void invalidCondition(boolean flag, String message) throws NotValidException {
        if (flag) {
            log.debug(message);
            throw new NotValidException();
        } else {
            return;
        }
    }
}
