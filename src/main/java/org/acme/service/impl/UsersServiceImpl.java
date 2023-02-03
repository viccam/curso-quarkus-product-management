package org.acme.service.impl;

import org.acme.controller.UserController;
import org.acme.dao.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class UsersServiceImpl implements org.acme.service.UsersService {

    //Instancia clase logger para registrar en consola
    static Logger logger = Logger.getLogger(UsersServiceImpl.class.getName());

    @PersistenceContext
    EntityManager em;

    /**
     * La API de Criterios nos permite construir un objeto de consulta de criterios programáticamente, donde podemos
     * aplicar diferentes tipos de reglas de filtrado y condiciones lógicas.
     * Cree una instancia de CriteriaBuilder llamando al método getCriteriaBuilder()
     * Cree una instancia de CriteriaQuery llamando al método CriteriaBuilder createQuery()
     * Cree una instancia de Query llamando al método Session createQuery()
     * Llame al método getResultList() del objeto de consulta , que nos da los resultados
     * @return
     */
    public List<User> list() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        query.from(User.class);
        logger.log(Level.INFO, "usuarios conultados exitosamente");
        return em.createQuery(query).getResultList();
    }

    public User create(User user) {
        if (Objects.nonNull(user.getId())) {
            logger.log(Level.FINER, "id no debe ser nullo");
            throw new IllegalStateException("Id should be null!");
        }
        em.persist(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        logger.log(Level.FINER, "consulta por username" + username);
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public User get(int userId) {
        return em.find(User.class, userId);
    }
}