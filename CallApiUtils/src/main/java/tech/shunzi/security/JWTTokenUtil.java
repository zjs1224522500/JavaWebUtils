package tech.shunzi.security;

import java.time.Instant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTTokenUtil //NOSONAR
{
    private final static Logger logger = LoggerFactory.getLogger(JWTTokenUtil.class);
    private static final String ONE_SPACE = " ";

    /**
     * Title: getJWTTokenModel Description:
     * 
     * @since
     * @param JWTToken
     *            jwttoken strings
     * @return JWTTokenModel
     */
    public static JWTTokenModel getJWTTokenModel(String JWTToken)
    {
        JWTTokenModel jwtTokenModel;
        try
        {
            String token = JWTToken.split(ONE_SPACE)[JWTToken.split(ONE_SPACE).length - 1];
            DecodedJWT jwt = JWT.decode(token);
            jwtTokenModel = new JWTTokenModel();
            jwtTokenModel.setUserName(jwt.getClaim("user_name").asString());
            jwtTokenModel.setEmail(jwt.getClaim("email").asString());
            jwtTokenModel.setExp(jwt.getClaim("exp").as(long.class));
            jwtTokenModel.setZid(jwt.getClaim("zid").asString());
            jwtTokenModel.setEmail(jwt.getClaim("email").asString());
        }
        catch (JWTDecodeException exception)
        {
            jwtTokenModel = null;
            logger.error("JWT decode exception in processing!", exception);
        }

        return jwtTokenModel;
    }

    public static boolean isTokenExpired(String JWTToken)
    {
        long expireDate = getJWTTokenModel(JWTToken).getExp();
        if (Instant.now().toEpochMilli() / 1000 < expireDate)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
