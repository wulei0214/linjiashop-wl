package cn.enilu.flash.cache;

import cn.enilu.flash.bean.core.AuthorizationUser;
import cn.enilu.flash.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录时，生成的Token与用户ID的对应关系
 */
@Service
public   class TokenCache {

    @Autowired
    private CacheDao cacheDao;

    public   void put(String token, Long idUser) {
        cacheDao.hset(CacheDao.SESSION,token, idUser);
    }

    public   Long get(String token) {
        return cacheDao.hget(CacheDao.SESSION,token,Long.class);
    }
    public Long getIdUser(){
        return cacheDao.hget(CacheDao.SESSION,HttpUtil.getToken(),Long.class );
    }

    public   void remove(String token) {
        cacheDao.hdel(CacheDao.SESSION,token);
        cacheDao.hdel(CacheDao.SESSION,token+"user");
    }

    /**
     * 强制下线：清空token的会话缓存，并加入黑名单使其立即失效
     */
    public void kick(String token) {
        remove(token);
        cacheDao.hset(CacheDao.KICK, token, System.currentTimeMillis());
    }

    /**
     * token是否已被强制下线（拉黑），黑名单保留时间大于token最大有效期
     */
    public boolean isKicked(String token) {
        try {
            return cacheDao.hget(CacheDao.KICK, token, Long.class) != null;
        } catch (Exception e) {
            //KICK缓存未配置等原因导致异常时按未拉黑处理，避免影响正常登录
            return false;
        }
    }

    public void setUser(String token, AuthorizationUser shiroUser){
        cacheDao.hset(CacheDao.SESSION,token+"user",shiroUser);
    }
    public AuthorizationUser getUser(String token){
        return cacheDao.hget(CacheDao.SESSION,token+"user", AuthorizationUser.class);
    }
}
