package jpabook.jpashop.domain.todo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodo is a Querydsl query type for Todo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodo extends EntityPathBase<Todo> {

    private static final long serialVersionUID = -452524853L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodo todo = new QTodo("todo");

    public final BooleanPath completionStatus = createBoolean("completionStatus");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final jpabook.jpashop.domain.keyword.QKeyword keyword;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ListPath<jpabook.jpashop.domain.like.Likes, jpabook.jpashop.domain.like.QLikes> likes = this.<jpabook.jpashop.domain.like.Likes, jpabook.jpashop.domain.like.QLikes>createList("likes", jpabook.jpashop.domain.like.Likes.class, jpabook.jpashop.domain.like.QLikes.class, PathInits.DIRECT2);

    public final jpabook.jpashop.domain.member.QMember member;

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QTodo(String variable) {
        this(Todo.class, forVariable(variable), INITS);
    }

    public QTodo(Path<? extends Todo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodo(PathMetadata metadata, PathInits inits) {
        this(Todo.class, metadata, inits);
    }

    public QTodo(Class<? extends Todo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new jpabook.jpashop.domain.keyword.QKeyword(forProperty("keyword")) : null;
        this.member = inits.isInitialized("member") ? new jpabook.jpashop.domain.member.QMember(forProperty("member")) : null;
    }

}

