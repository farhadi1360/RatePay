package com.ratepay.bugtracker.services;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.core.service.MainServiceSQLMode;

import java.io.Serializable;

public interface UserService <M, ID extends Serializable> extends MainServiceSQLMode<M, ID> {
}
