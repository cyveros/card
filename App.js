/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button, NativeModules} from 'react-native';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

const { PBSCHCEManager } = NativeModules;


export default class App extends Component {
  state = {
    token: null
  }

  async componentDidMount() {
    let token = await PBSCHCEManager.getStoredUniqueToken();

    this.setState({ token });
    console.log('existed token is:', token);
  }

  overwriteToken = async () => {
    await PBSCHCEManager.addUniqueToken('ABCDEF');

    this.setState({ token: 'ABCDEF' });

    console.log('current token is:', await PBSCHCEManager.getStoredUniqueToken());
  }

  render() {
    const {token} = this.state;

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <Text>token is: {token ? token : 'default'}</Text>
        <Button
          title={'Change user token'}
          onPress={this.overwriteToken}
        />
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
});
