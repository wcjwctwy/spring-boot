package wang.congjun.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 为 on 2017-6-9.
 */

@Component
@Slf4j
public class MySessionRegistryImpl implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {


    private static final String SESSIONIDS = "sessionIds";

    private static final String PRINCIPALS = "principals";

    private Map<String,Object> redisTemplate = new HashMap<>();

//    private final ConcurrentMap<Object, Set<String>> principals = new ConcurrentHashMap();
//    private final Map<String, SessionInformation> sessionIds = new ConcurrentHashMap();

    public MySessionRegistryImpl() {
    }

    @Override
    public List<Object> getAllPrincipals() {
        return new ArrayList<>(this.getPrincipalsKeySet());
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal =  this.getPrincipals(((UserDetails)principal).getUsername());
        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        } else {
            List<SessionInformation> list = new ArrayList(sessionsUsedByPrincipal.size());
            Iterator var5 = sessionsUsedByPrincipal.iterator();

            while (true) {
                SessionInformation sessionInformation;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return list;
                        }

                        String sessionId = (String) var5.next();
                        sessionInformation = this.getSessionInformation(sessionId);
                    } while (sessionInformation == null);
                } while (!includeExpiredSessions && sessionInformation.isExpired());

                list.add(sessionInformation);
            }
        }
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        return  this.getSessionInfo(sessionId);
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        String sessionId = event.getId();
        this.removeSessionInformation(sessionId);
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }

    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");
        if (log.isDebugEnabled()) {
            log.debug("Registering session " + sessionId + ", for principal " + principal);
        }

        if (this.getSessionInformation(sessionId) != null) {
            this.removeSessionInformation(sessionId);
        }

        this.addSessionInfo(sessionId, new SessionInformation(principal, sessionId, new Date()));

        Set<String> sessionsUsedByPrincipal = (Set) this.getPrincipals(principal.toString());
        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = new CopyOnWriteArraySet<>();
            Set<String> prevSessionsUsedByPrincipal = (Set) this.putIfAbsentPrincipals(principal.toString(), sessionsUsedByPrincipal);
            if (prevSessionsUsedByPrincipal != null) {
                sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
            }
        }

        ((Set) sessionsUsedByPrincipal).add(sessionId);
        this.putPrincipals(principal.toString(), sessionsUsedByPrincipal);
        if (log.isTraceEnabled()) {
            log.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
        }

    }

    @Override
    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            if (log.isTraceEnabled()) {
                log.debug("Removing session " + sessionId + " from set of registered sessions");
            }

            this.removeSessionInfo(sessionId);
            Set<String> sessionsUsedByPrincipal = (Set) this.getPrincipals(info.getPrincipal().toString());
            if (sessionsUsedByPrincipal != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Removing session " + sessionId + " from principal's set of registered sessions");
                }

                sessionsUsedByPrincipal.remove(sessionId);
                if (sessionsUsedByPrincipal.isEmpty()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Removing principal " + info.getPrincipal() + " from registry");
                    }

                    this.removePrincipal(((UserDetails)info.getPrincipal()).getUsername());
                }

                if (log.isTraceEnabled()) {
                    log.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
                }

            }
        }
    }


    public void addSessionInfo(final String sessionId, final SessionInformation sessionInformation) {
        log.info("[存储sessionID]：{}",sessionId);
        redisTemplate.put(sessionId, sessionInformation);
    }

    public SessionInformation getSessionInfo(final String sessionId) {
        log.info("[获取sessionID]：{}",sessionId);
        return (SessionInformation)redisTemplate.get(sessionId);
    }

    public void removeSessionInfo(final String sessionId) {
        log.info("[删除sessionID]：{}",sessionId);
        redisTemplate.remove(sessionId);
    }

    public Set<String> putIfAbsentPrincipals(final String key, final Set<String> set) {
        log.info("[putIfAbsentPrincipals]：putIfAbsentPrincipals");
        redisTemplate.putIfAbsent(key, set);
        return (Set<String>)redisTemplate.get(key);
    }

    public void putPrincipals(final String key, final Set<String> set) {
        log.info("[putPrincipals]：putPrincipals");
        redisTemplate.put(key,set);
    }

    public Set<String> getPrincipals(final String key) {
        log.info("[getPrincipals]：getPrincipals");
        return (Set<String>)redisTemplate.get(key);
    }

    public Set<String> getPrincipalsKeySet() {
        log.info("[getPrincipalsKeySet]：getPrincipalsKeySet");
        return redisTemplate.keySet();
    }

    public void removePrincipal(final String key) {
        log.info("[removePrincipal]：removePrincipal");
        redisTemplate.remove(key);
    }


}