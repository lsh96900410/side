package jpabook.jpashop.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1075558965L;

    public static final QMember member = new QMember("member1");

    public final StringPath career = createString("career");

    public final StringPath developerPosition = createString("developerPosition");

    public final StringPath email = createString("email");

    public final StringPath fileLocation = createString("fileLocation");

    public final ListPath<jpabook.jpashop.domain.follow.Follow, jpabook.jpashop.domain.follow.QFollow> followings = this.<jpabook.jpashop.domain.follow.Follow, jpabook.jpashop.domain.follow.QFollow>createList("followings", jpabook.jpashop.domain.follow.Follow.class, jpabook.jpashop.domain.follow.QFollow.class, PathInits.DIRECT2);

    public final ListPath<jpabook.jpashop.domain.follow.Follow, jpabook.jpashop.domain.follow.QFollow> follows = this.<jpabook.jpashop.domain.follow.Follow, jpabook.jpashop.domain.follow.QFollow>createList("follows", jpabook.jpashop.domain.follow.Follow.class, jpabook.jpashop.domain.follow.QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<jpabook.jpashop.domain.like.Likes, jpabook.jpashop.domain.like.QLikes> likes = this.<jpabook.jpashop.domain.like.Likes, jpabook.jpashop.domain.like.QLikes>createList("likes", jpabook.jpashop.domain.like.Likes.class, jpabook.jpashop.domain.like.QLikes.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final StringPath role = createString("role");

    public final DatePath<java.time.LocalDate> signUpDate = createDate("signUpDate", java.time.LocalDate.class);

    public final ListPath<jpabook.jpashop.domain.todo.Todo, jpabook.jpashop.domain.todo.QTodo> todos = this.<jpabook.jpashop.domain.todo.Todo, jpabook.jpashop.domain.todo.QTodo>createList("todos", jpabook.jpashop.domain.todo.Todo.class, jpabook.jpashop.domain.todo.QTodo.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalViewCount = createNumber("totalViewCount", Integer.class);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

