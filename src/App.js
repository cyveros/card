/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, View, Button, NativeModules} from 'react-native';

const { PBSCHCEManager } = NativeModules;


export default class App extends Component {
  state = {
    token: null,
    userToken: null,
    cmd: null
  }

  constructor(props) {
    super(props);

    this.timeHandler = setInterval(this.onTime, 4000);
  }

  async componentDidMount() {
    let token = await PBSCHCEManager.getStoredUniqueToken();

    this.setState({ token });
    console.log('existed token is:', token);
  }

  componentWillUnmount() {
    this.stopTime();
  }

  overwriteToken = async () => {
    const { userToken = null } = this.state;

    if (userToken) {
      console.log('user token is:', userToken);
      await PBSCHCEManager.addUniqueToken(userToken);

      this.setState({
        token: userToken
      });

      console.log('current token is:', await PBSCHCEManager.getStoredUniqueToken());
    }
  }

  onTime = async () => {
    let cmd = await PBSCHCEManager.getApduCmd();

    this.setState({ cmd });

    console.log('apdu cmd is:', cmd);
  }

  stopTime = () => {
    clearInterval(this.timeHandler);
  }

  inputUserToken = (userToken) => {
    this.setState({ userToken });
  }

  render() {
    const { token, cmd } = this.state;

    return (
      <View style={styles.container}>
        <View style={{
          flexDirection: 'row'
        }}>
          <TextInput
            style={{flex: 1, borderBottomWidth: 1}}
            onChangeText={this.inputUserToken}
            placeholder={'Put new user token here'}
            autoCapitalize={'characters'}
          />
          <Button title={'Push'} onPress={this.overwriteToken} />
        </View>
        <View style={{flex: 1, padding: 10, flexDirection: 'row'}}>
          <View style={{
            flex: 1,
            flexDirection: 'column'
          }}>
            <View style={{flex: 1}}><Text>Token:</Text></View><View style={{flex: 1}}><Text>{token ? token : 'default'}</Text></View>
          </View>
          <View style={{
            flex: 1,
            flexDirection: 'column'
          }}>
            <View style={{flex: 1}}><Text>APDU cmd:</Text></View><View style={{flex: 1}}><Text>{cmd ? cmd : 'default'}</Text></View>
          </View>
        </View>
        <Button
          title={'Change user token'}
          onPress={this.overwriteToken}
        />
        <Button
          title={'stop tracking apdu cmd'}
          onPress={this.stopTime}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
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
