<template>
  <section class="container">
    <head-bar headtext="用户登录"></head-bar>
    <form class="form-horizontal">
      <div class="form-group">
        <label class="col-xs-2 control-label col-md-2">用户名</label>
        <div class="col-xs-10 col-md-8">
          <input type="text" class="form-control" id="username" placeholder="User">
        </div>
      </div>
      <div class="form-group">
        <label class="col-xs-2 control-label col-md-2">密码</label>
        <div class="col-xs-10 col-md-8">
          <input type="password" class="form-control" id="password" placeholder="Password">
        </div>
      </div>
      <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10 col-md-8">
          <div class="checkbox-inline">
            <label>
              <input type="checkbox">记住我
            </label>
          </div>
          <a class="pull-right">
            Jaccount 登录
          </a>
        </div>
      </div>
      <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10 col-md-8">
          <button type="button" @click="login" class="btn btn-default" id="login-button">登录</button>
          <router-link :to="{ name: 'Register' }">
            <button type="button" class="btn btn-default">注册</button>
          </router-link>
        </div>
      </div>
    </form>
  </section>
</template>

<script>
import HeadBar from '@/components/components/head-bar'

export default {
  name: 'login',
  components: {
    HeadBar
  },
  methods: {
    login () {
      var pwd = document.getElementById('password').value
      var name = document.getElementById('username').value
      this.$http.post('/loginhandle', { name: name, pwd: pwd })
        .then((response) => {
          if (response.data.id !== 0) {
            this.data.LoginUser.id = response.data.id
            var url = window.location.href
            url = url.substring(0, url.length - 5)
            window.location.href = url + 'user/info'
          } else {
            alert('用户名或密码错误')
          }
        })
        .catch((error) => {
          if (error.response.status === 400) {
            alert('你被ban了')
          }
        })
    },
    keyListener (e) {
      if (e.keyCode === 13) {
        this.button.click()
      }
    }
  },
  mounted () {
    document.onkeydown = this.keyListener
    var buttons = document.getElementsByTagName('button')
    for (var button of buttons) {
      if (button.innerHTML === '登录') {
        this.button = button
        break
      }
    }
    this.$http.post('/loginstatus')
      .then((response) => {
        if (response.data.id !== 0) {
          this.data.LoginUser.id = response.data.id
          var url = window.location.href
          url = url.substring(0, url.length - 5)
          window.location.href = url + 'user/info'
        }
      })
  }
}
</script>

<style scoped>
#login-button {
  margin-right: 2%;
}
</style>
