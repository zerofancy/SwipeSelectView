package top.ntutn.swipeselectview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import top.ntutn.swipeselectview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        val words = """
            善解人意
            小鸟依人
            聪明
            可爱
            文静
            完美
            活泼
            冰雪聪明
            天使
            才女
            美丽
            活泼
            可人
            完美
            羞涩
            艳丽
            妍丽
            美艳
            富丽
            瑰丽
            秀丽
            活泼
            鲜艳
            绚丽
            漂亮
            摩登
            大方
            时兴
            入时
            时髦
            完美
            锦绣
            姣好
            俊美
            俊丽
            俊秀
            俊俏
            斑斓
            大度
            文雅
            标致
            美好
            优美
            艳丽
            完美
            标致
            美妙
            美观
            俊俏 
            气质
            端庄
            明媚皓齿
        """.trimIndent().lines()
        binding.contentMain.chipGroup.apply {
            isSelectionRequired
            isSingleSelection = false
            words.forEach { word ->
                addView(Chip(context).also { chip ->
                    chip.text = word.trimIndent()
                    chip.setOnClickListener {
                        it.isSelected = !it.isSelected
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}