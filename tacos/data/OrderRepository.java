package tacos.tacos03.tacos.data;

import java.util.Optional;

import tacos.tacos03.tacos.TacoOrder;

public interface OrderRepository {

  TacoOrder save(TacoOrder order);

  Optional<TacoOrder> findById(Long id);

}
