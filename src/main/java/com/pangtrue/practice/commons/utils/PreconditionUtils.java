package com.pangtrue.practice.commons.utils;

import com.pangtrue.practice.commons.exception.NotValidException;
import lombok.extern.slf4j.Slf4j;

/**
 * User: SeungHo Lee (seung7642@gmail.com)
 * Date: 2020. 2. 6.
 * Time: 오전 9:50
 */
@Slf4j
public class PreconditionUtils {

    public static void invalidCondition(boolean isValid, String message) throws NotValidException {
        if (isValid) {
            log.debug(message);
            throw new NotValidException();
        }
    }
}
