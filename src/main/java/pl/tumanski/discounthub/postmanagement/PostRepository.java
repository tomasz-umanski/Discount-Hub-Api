package pl.tumanski.discounthub.postmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;

@Repository
interface PostRepository extends JpaRepository<Post, Long> {
}
