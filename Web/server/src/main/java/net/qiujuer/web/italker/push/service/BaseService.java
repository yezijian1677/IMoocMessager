package net.qiujuer.web.italker.push.service;

import net.qiujuer.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * @author augenye
 * @date 2019-10-06 16:09
 */
public class BaseService {

    @Context
    protected SecurityContext securityContext;

    /**
     * 从上下文中直接获取信息
     * @return
     */
    protected User getSelf() {
        return (User) securityContext.getUserPrincipal();
    }
}
