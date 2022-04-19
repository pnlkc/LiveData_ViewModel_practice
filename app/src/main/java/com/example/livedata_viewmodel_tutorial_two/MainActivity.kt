package com.example.livedata_viewmodel_tutorial_two

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.livedata_viewmodel_tutorial_two.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /*
    ViewModel 초기화는 여러 방법으로 할 수 있음 (펼쳐서 확인)

    1. ViewModelProvider 를 이용해 초기화하기 <- 개발하는 정대리 방법
       이 때는 ViewModel이 MainActivity(초기화한 Activity)의 Lifecycle을 따르는 뷰모델이 됨

    2. by viewModels() 를 이용해 초기화 하기 <- 여기서 사용된 코드
       이 때는 ViewModel이 초기화 되는 Activity나 Fragment의 Lifecycle을 따르는 뷰모델이 됨

    3. by activityViewModels() 를 이용해 초기화 하기
       이 방법은 Fragment에서만 사용할 수 있다! (Activity에서 사용불가!)
       이 때는 ViewModel이 초기화 되는 Fragment를 포함한 Acticity의 Lifecycle을 따르는 뷰모델이 됨
       이 방법을 사용하면 같은 Activity를 가지는 Fragment 사이에 SharedViewModel로 사용 가능
     */

    // viewModel 초기화
    private val myNumberViewModel: MyNumberViewModel by viewModels()

    /*
    // 개발하는 정대리 강의에서는 ViewModelProvider를 이용해서 초기화
    lateinit var myNumberViewModel: MyNumberViewModel
     */



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        // 개발하는 정대리 -> ViewModelProvider를 이용해서 초기화
        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)
         */

        /*
        // Databinding을 이용하면 아래의 observe 코드 없이 바로 값 변경 가능
        // Databinding 이용하려면 아래 코드와 xml 수정이 필요
        binding.lifecycleOwner = this
        binding.viewModel = myNumberViewModel
        */

        // View(여기는 Activity)에서 viewModel 객체의 data를 가져와서 observe 하는 방식
        // Fragment의 경우 owner를 this가 아닌 viewLifecycleOwner로 사용해야 됨
        myNumberViewModel.currentValue.observe(this, Observer {
            binding.numberTextView.text = it.toString()
        })

        // 리스너 연결
        binding.addButton.setOnClickListener(this)
        binding.subButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        // setOnClickListener를 람다식으로 사용하는 매소드는 아래처럼 변수만들면 Null 에러 뜸
        val userInput = binding.userinputEdittext.text.toString().toInt()

        // 뷰모델에 라이브 데이터 값을 변경하는 메소드 실행
        when (view) {
            binding.addButton -> {
                myNumberViewModel.updateValue(ActionType.PLUS, userInput)
            }
            binding.subButton -> {
                myNumberViewModel.updateValue(ActionType.MINUS, userInput)
            }
        }
    }
}