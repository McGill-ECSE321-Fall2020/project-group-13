<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">Logo</b-navbar-brand>

      <b-container>
        <b-row class="align-items-center">
          <b-col>
            <b-nav-form>
              <b-container>
                <b-row class="flex-nowrap align-items-center">
                  <b-col class="m-0 p-0">
                    <b-form-input
                      size="sm"
                      class="mr-sm-2 mr-auto ml-auto"
                      placeholder="Search"
                    ></b-form-input>
                  </b-col>
                  <b-col class="m-0 p-0">
                    <b-button size="sm" class="my-2 my-sm-0" type="submit"
                      >Search</b-button
                    >
                  </b-col>
                </b-row>
              </b-container>
            </b-nav-form>
          </b-col>
          
            <b-nav-item-dropdown text="Lang" right class="align-self-center">
              <b-dropdown-item href="#">EN</b-dropdown-item>
              <b-dropdown-item href="#">ES</b-dropdown-item>
              <b-dropdown-item href="#">RU</b-dropdown-item>
              <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>
      
        </b-row>
      </b-container>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown text="Lang" right>
            <b-dropdown-item href="#">EN</b-dropdown-item>
            <b-dropdown-item href="#">ES</b-dropdown-item>
            <b-dropdown-item href="#">RU</b-dropdown-item>
            <b-dropdown-item href="#">FA</b-dropdown-item>
          </b-nav-item-dropdown>

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>User</em>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script></script>
<style></style>
