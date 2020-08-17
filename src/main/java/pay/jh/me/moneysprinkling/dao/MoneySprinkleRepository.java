package pay.jh.me.moneysprinkling.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pay.jh.me.moneysprinkling.endity.MoneySprinkle;

public interface MoneySprinkleRepository extends JpaRepository<MoneySprinkle, String> {
}
