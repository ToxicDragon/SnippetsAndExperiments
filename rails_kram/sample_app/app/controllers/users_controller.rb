class UsersController < ApplicationController
  
  def show
    @user = User.find(params[:id])
    @title = "Show"
  end
  
  
  def new
    @title = "Sign up"
  end
  
end
