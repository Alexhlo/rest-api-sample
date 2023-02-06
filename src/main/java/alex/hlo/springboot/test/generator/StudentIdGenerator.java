package alex.hlo.springboot.test.generator;

import net.bytebuddy.utility.RandomString;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class StudentIdGenerator implements IdentifierGenerator {

    public static final String NAME = "StudentId";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object)
            throws HibernateException {

        return new RandomString(8).nextString();
    }
}
