package pay.jh.me.moneysprinkling.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pay.jh.me.moneysprinkling.entity.MoneySprinkle;

import java.util.Optional;

public interface MoneySprinkleRepository extends JpaRepository<MoneySprinkle, String> {

    Optional<MoneySprinkle> findByToken(String token);
}
