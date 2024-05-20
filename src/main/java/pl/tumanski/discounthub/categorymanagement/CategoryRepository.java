package pl.tumanski.discounthub.categorymanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;

interface CategoryRepository extends JpaRepository<Category, Long> {
}
