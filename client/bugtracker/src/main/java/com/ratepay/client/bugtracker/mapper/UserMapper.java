package com.ratepay.client.bugtracker.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserModel, User> {
}
