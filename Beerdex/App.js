/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Button,
  AlertIOS
} from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {

    buttonPressed() {
        Alert.alert('Button pressed')
    }

    getMovies() {
        return fetch('http://localhost:3000/employees')

            .then((response) => response.json())
            .then((responseJson) => {
              AlertIOS.alert(
                  'GET Response',
                  'Search query -> ' + responseJson
              )
            })

            .catch((error) => {
              console.error(error);
            });
    }

    render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit App.js
        </Text>
        <View style = {styles.buttonContainer}>
            <Button
                onPress = {this.getMovies}
                title="Test networking"
                color = 'blue'
            />
        </View>

        <Text style={styles.instructions}>
          {instructions}
        </Text>
      </View>
    );
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  buttonContainer: {
    margin: 20
  },
  alternativeLayoutButtonContainer: {
    margin: 20,
    flexDirection: 'row',
    justifyContent: 'space-between'
  }
});
