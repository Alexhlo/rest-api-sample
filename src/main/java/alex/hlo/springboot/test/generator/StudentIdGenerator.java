package alex.hlo.springboot.test.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class StudentIdGenerator implements IdentifierGenerator {

    public static final String NAME = "StudentId";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object)
            throws HibernateException {

        return RandomStringUtils.random(8);
    }
}
