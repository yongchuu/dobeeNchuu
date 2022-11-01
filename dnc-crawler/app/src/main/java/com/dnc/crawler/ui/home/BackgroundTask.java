package com.dnc.crawler.ui.home;

import android.os.AsyncTask;
import android.widget.ProgressBar;

//새로운 TASK정의 (AsyncTask)
// < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)
class BackgroundTask extends AsyncTask<Integer , Integer , Integer> {
    BackgroundTask task;
    int value;
    ProgressBar progress;

    //초기화 단계에서 사용한다. 초기화관련 코드를 작성했다.
    protected void onPreExecute() {
        value = 0;
        progress.setProgress(value);
    }

    //스레드의 백그라운드 작업 구현
//여기서 매개변수 Intger ... values란 values란 이름의 Integer배열이라 생각하면된다.
//배열이라 여러개를 받을 수 도 있다. ex) excute(100, 10, 20, 30); 이런식으로 전달 받으면 된다.
    protected Integer doInBackground(Integer ... values) {
//isCancelled()=> Task가 취소되었을때 즉 cancel당할때까지 반복
        while (isCancelled() == false) {
            value++;
//위에 onCreate()에서 호출한 excute(100)의 100을 사용할려면 이런식으로 해줘도 같은 결과가 나온다.
//밑 대신 이렇게해도됨 if (value >= values[0].intValue())
            if (value >= 100) {
                break;
            } else {
//publishProgress()는 onProgressUpdate()를 호출하는 메소드(그래서 onProgressUpdate의 매개변수인 int즉 Integer값을 보냄)
//즉, 이 메소드를 통해 백그라운드 스레드작업을 실행하면서 중간중간 UI에 업데이트를 할 수 있다.

//백그라운드에서는 UI작업을 할 수 없기 때문에 사용
                publishProgress(value);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        }

        return value;
    }

    //UI작업 관련 작업 (백그라운드 실행중 이 메소드를 통해 UI작업을 할 수 있다)
//publishProgress(value)의 value를 값으로 받는다.values는 배열이라 여러개 받기가능
    protected void onProgressUpdate(Integer ... values) {
        progress.setProgress(values[0].intValue());
//        textView.setText("현재 진행 값 : " + values[0].toString());
    }


    //이 Task에서(즉 이 스레드에서) 수행되던 작업이 종료되었을 때 호출됨
    protected void onPostExecute(Integer result) {
        progress.setProgress(0);
//        textView.setText("완료되었습니다");
    }

    //Task가 취소되었을때 호출
    protected void onCancelled() {
        progress.setProgress(0);
//        textView.setText("취소되었습니다");
    }
}