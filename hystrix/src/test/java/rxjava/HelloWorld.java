package rxjava;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.List;

public class HelloWorld {
    public void hello(List<String> names) {
        Observable<String> obervable = Observable.from(names);
        obervable.subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println("hello " + s);
            }
        });
    }

    @Test
    public void create () {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribe(new Subscriber<Integer>() {
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    @Test
    public void test() {
        List<String> names = Lists.newArrayList("xue", "chong", "yang");
        hello(names);
        names.add("123");
    }
}
