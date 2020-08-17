package pay.jh.me.moneysprinkling.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pay.jh.me.moneysprinkling.endity.MoneySprinkleDtl;

public interface MoneySprinkleDtlRepository extends JpaRepository<MoneySprinkleDtl, String> {

    MoneySprinkleDtl findTop1ByToken(String token);
}
